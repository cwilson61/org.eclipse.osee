/*********************************************************************
 * Copyright (c) 2021 Boeing
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Boeing - initial API and implementation
 **********************************************************************/

import { A11yModule } from '@angular/cdk/a11y';
import { AsyncPipe, NgFor, NgIf } from '@angular/common';
import {
	Component,
	OnDestroy,
	OnChanges,
	Input,
	Output,
	EventEmitter,
	Inject,
	SimpleChanges,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { RouterLink } from '@angular/router';
import {
	CurrentStructureService,
	STRUCTURE_SERVICE_TOKEN,
	WarningDialogService,
} from '@osee/messaging/shared';
import type { element, PlatformType } from '@osee/messaging/shared';
import { MatOptionLoadingComponent } from '@osee/shared/components';
import {
	Subject,
	combineLatest,
	switchMap,
	of,
	BehaviorSubject,
	share,
	debounceTime,
	distinctUntilChanged,
	map,
	tap,
	scan,
	iif,
	ReplaySubject,
} from 'rxjs';

@Component({
	selector: 'osee-messaging-edit-element-field',
	templateUrl: './edit-element-field.component.html',
	styleUrls: ['./edit-element-field.component.sass'],
	standalone: true,
	imports: [
		MatFormFieldModule,
		FormsModule,
		NgIf,
		NgFor,
		MatSelectModule,
		MatOptionModule,
		MatInputModule,
		MatAutocompleteModule,
		MatOptionLoadingComponent,
		MatButtonModule,
		MatIconModule,
		AsyncPipe,
		A11yModule,
		RouterLink,
	],
})
export class EditElementFieldComponent<T extends keyof element = any>
	implements OnDestroy, OnChanges
{
	private _done = new Subject();
	availableTypes = this.structureService.types;
	@Input() structureId: string = '';
	@Input() elementId: string = '';
	@Input() header: (T & string) | '' = '';
	@Input() value: T = {} as T;
	@Input() elementStart: number = 0;
	@Input() elementEnd: number = 0;
	@Input() editingDisabled: boolean = false;

	@Input() platformTypeId: string = '';
	@Output() contextMenu = new EventEmitter<MouseEvent>();

	private _value: Subject<T> = new Subject();
	private _immediateValue: Subject<T> = new Subject();
	private _units: Subject<string> = new Subject();
	_element: Partial<element> = {
		id: this.elementId,
	};
	_location = combineLatest([
		this.structureService.branchType,
		this.structureService.BranchId,
	]).pipe(switchMap(([type, id]) => of({ type: type, id: id })));
	/**
	 * Type ahead value when editing which platform type is set for the given element
	 */
	private _typeValue: BehaviorSubject<string> = new BehaviorSubject('');
	private _sendValue = this._value.pipe(
		share(),
		debounceTime(500),
		distinctUntilChanged(),
		map((x) => (this._element[this.header] = x)),
		tap(() => {
			this._element.id = this.elementId;
		})
	);
	private _updateUnits = this._units.pipe(
		share(),
		debounceTime(500),
		distinctUntilChanged(),
		switchMap((unit) =>
			this.structureService.updatePlatformTypeValue({
				id: this.platformTypeId,
				interfacePlatformTypeUnits: unit,
			})
		)
	);
	private _immediateUpdateValue = this._immediateValue.pipe(
		share(),
		debounceTime(500),
		distinctUntilChanged(),
		map((x) => (this._element[this.header] = x)),
		tap(() => {
			this._element.id = this.elementId;
		}),
		switchMap(() => this.warningService.openElementDialog(this._element)),
		switchMap((value) =>
			this.structureService.partialUpdateElement(value, this.structureId)
		)
	);
	private _focus = new Subject<string | null>();
	private _updateValue = combineLatest([this._sendValue, this._focus]).pipe(
		scan(
			(acc, curr) => {
				if (acc.type === curr[1]) {
					acc.count++;
				} else {
					acc.count = 0;
					acc.type = curr[1];
				}
				acc.value = curr[0];
				return acc;
			},
			{ count: 0, type: '', value: undefined } as {
				count: number;
				type: string | null;
				value: T | undefined;
			}
		),
		switchMap((update) =>
			iif(
				() => update.type === null,
				of(true).pipe(
					switchMap(() =>
						this.warningService.openElementDialog(this._element)
					),
					switchMap((value) =>
						this.structureService.partialUpdateElement(
							value,
							this.structureId
						)
					)
				),
				of(false)
			)
		)
	);
	/**
	 * State of when auto complete is initial opened to defer data loading
	 */
	openTypeAutoComplete = new ReplaySubject<void>();
	filteredTypes = (pageNum: string | number) =>
		this.openTypeAutoComplete.pipe(
			distinctUntilChanged(),
			switchMap((_) =>
				this._typeValue.pipe(
					debounceTime(500),
					switchMap((typeAhead) =>
						this.structureService.getPaginatedFilteredTypes(
							this.isString(typeAhead)
								? typeAhead.toLowerCase()
								: (typeAhead as unknown as string),
							pageNum
						)
					)
				)
			)
		);
	private _type: Subject<PlatformType> = new Subject();
	private _sendType = this._type.pipe(
		share(),
		debounceTime(500),
		distinctUntilChanged(),
		switchMap((value) =>
			this.structureService.changeElementPlatformType(
				this.structureId,
				this.elementId,
				value
			)
		)
	);

	applics = this.structureService.applic;
	units = () => this.structureService.units;
	menuPosition = {
		x: '0',
		y: '0',
	};
	constructor(
		@Inject(STRUCTURE_SERVICE_TOKEN)
		private structureService: CurrentStructureService,
		private warningService: WarningDialogService
	) {
		this._updateValue.subscribe();
		this._immediateUpdateValue.subscribe();
		this._sendType.subscribe();
		this._updateUnits.subscribe();
	}
	ngOnChanges(changes: SimpleChanges): void {
		this.updateTypeAhead(this.value);
	}
	ngOnDestroy(): void {
		this._done.next(true);
	}
	updateElement(header: string, value: T) {
		this._value.next(value);
	}
	updateImmediately(header: string, value: T) {
		this._immediateValue.next(value);
	}
	updateType(value: PlatformType) {
		this._type.next(value);
		this.updateTypeAhead(value.name);
	}

	updateTypeAhead(value: any) {
		this._typeValue.next(value);
		this.filteredTypes = (pageNum: string | number) =>
			this.openTypeAutoComplete.pipe(
				distinctUntilChanged(),
				switchMap((_) =>
					this._typeValue.pipe(
						debounceTime(500),
						switchMap((typeAhead) =>
							this.structureService.getPaginatedFilteredTypes(
								this.isString(typeAhead)
									? typeAhead.toLowerCase()
									: (typeAhead as unknown as string),
								pageNum
							)
						)
					)
				)
			);
	}

	compareApplics(o1: any, o2: any) {
		return o1.id === o2.id && o1.name === o2.name;
	}

	openMenu(event: MouseEvent, location: T) {
		event.preventDefault();
		this.contextMenu.emit(event);
	}
	isString(val: T | string): val is string {
		return typeof val === 'string' || val instanceof String;
	}
	focusChanged(event: string | null) {
		this._focus.next(event);
	}
	updateUnits(event: string) {
		this._units.next(event);
	}
	autoCompleteOpened() {
		this.openTypeAutoComplete.next();
	}
}
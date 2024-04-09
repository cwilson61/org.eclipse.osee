#
# Copyright (c) 2024 Boeing
#
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which is available at https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
#     Boeing - initial API and implementation
# 

load("@aspect_bazel_lib//lib:repo_utils.bzl", "repo_utils")

def _bat_host_os_alias_impl(repository_ctx):
    ext = ".exe" if repo_utils.is_windows(repository_ctx) else ""

    # Base BUILD file for this repository
    repository_ctx.file("BUILD.bazel", """# Generated by bat_repo_host_os_alias.bzl
package(default_visibility = ["//visibility:public"])
# aliases for exports_files

# aliases for other aliases
alias(name = "bat_bin",                actual = "@{bat_repository}_{os_name}//:bat_bin")
alias(name = "bat",                    actual = "@{bat_repository}_{os_name}//:bat")
alias(name = "bat_files",              actual = "@{bat_repository}_{os_name}//:bat_files")
exports_files([
    "index.bzl",
    "bin/bat{ext}",
])
""".format(
        bat_repository = repository_ctx.attr.user_bat_repository_name,
        os_name = repo_utils.os(repository_ctx),
        ext = ext,
    ))

    repository_ctx.symlink("../{bat_repository}_{os_name}/bin/bat{ext}".format(
        bat_repository = repository_ctx.attr.user_bat_repository_name,
        os_name = repo_utils.os(repository_ctx),
        ext = ext,
    ), "bin/bat" + ext)

    # index.bzl file for this repository
    repository_ctx.file("index.bzl", content = """# Generated by bat_repo_host_os_alias.bzl
host_platform="{host_platform}"
""".format(host_platform = repo_utils.os(repository_ctx)))

bat_repo_host_os_alias = repository_rule(
    _bat_host_os_alias_impl,
    doc = """Creates a repository with a shorter name meant for the host platform, which contains

    - A BUILD.bazel file declaring aliases to the host platform's bat binaries
    - index.bzl containing some constants
    """,
    attrs = {
        "user_bat_repository_name": attr.string(
            default = "bat",
            doc = "User-provided name from the workspace file, eg. bat16",
        ),
    },
)
#!/bin/bash

# script to find all plugins and tests that changed till the last release
# parameter: <feature freeze branch name> e.g., 3.1.x

git diff --name-only release..$1 -- plugins/ | cut -d'/' -f2 | sort -u
git diff --name-only release..$1 -- tests/ | cut -d'/' -f2 | sort -u

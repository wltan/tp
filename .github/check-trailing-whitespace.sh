#!/bin/sh
# Checks for trailing whitespace
echo "Checking for trailing whitespace..."

git grep --cached -I -n --no-color -P '[ \t]+$' -- ':/' |
awk '
    BEGIN {
        FS = ":"
        OFS = ":"
        ret = 0
    }
    {
        ret = 1
        print "ERROR", $1, $2, " trailing whitespace."
    }
    END {
        exit ret
    }
'

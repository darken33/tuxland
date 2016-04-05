#!/bin/sh
#
# Start script For tuxland v0.1

# Enter Tuxland's directory. Default is the current directory.
# In multi user mode this can be any directory (full path!).
#
#TUXLANDDIR=`pwd`

TUXLANDDIR=`pwd`

# Where can I find java?
# Default is just looking at $PATH
# (remember option -j)
#JAVA=`which java`

JAVA=`which java`

########################################################################
#        DO NOT EDIT ANY MORE UNTIL YOU KNOW WHAT YOU'RE DOING!        #
########################################################################

            
# check top install directory

if [ ! -d $TUXLANDDIR ]
then
  echo "INSTALL DIRECTORY NOT FOUND: $TUXLANDDIR" >&2
  exit 1
fi

# check top install directory
if [ -z $JAVA ]
then
  echo "JAVA NOT FOUND." >&2
  exit 1
fi

# Execute xset -r
xset -r

# Execute Tuxland
$JAVA -classpath "$TUXLANDDIR/lib/Tuxland.jar" darken.games.tuxland.Tuxland

# Execute xset r
xset r

# Output the help

exit 0 

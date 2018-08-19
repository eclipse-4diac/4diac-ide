#!bin/bash
# you need curl and checklink (w3c) to make this work
# sudo apt-get install curl 
# perl -MCPAN -e 'install W3C::LinkChecker'
#
#
# You can pass files or folders as arguments to filter the checking.
# If no argument is passed, the local folder is used
# Warnings are treated as errors

#flags to enable/disable chekers
CHECK_LINKS=false 	    #check broken links
CHECK_HTML=false	    #check html 5 syntax errors 	
CHECK_SYNTAX=false	    #check user defined syntax practices
CHECK_LAST=false	    #check if lastModified is present
FIND_RECURSIVELY=false	#check if lastModified is present
USAGE_REQ=false
W_ERROR=false
USER_CHECK_COLOR="1;33" #yellow
USER_CHECK_TEXT="WARNING"

FOLDERS=""
FILES=""

function usage(){
    echo "Usage $0 [-l] [-t] [-s] [-L] [-h] [-r] [FILES] [FOLDERS]"
	echo ""
	echo "Check for different issues in webpages. HTML and XML types are supported. XML only support the -l option, other will be ignored. If no file or folder is given, the current directory is taken."
	echo ""
	echo "   -a       Enable all checks"
	echo "   -l       Enable check for broken links"
	echo "   -t       Enable check for HTML 5 syntax"
	echo "   -s       Enable check for user defined syntax. This flag is treated as warning unless -w is specified"
	echo "   -L       Enable check for lastModified tag in the file"
	echo "   -r       Look in the folders recursively. Default is false"
	echo "   -w       Treat warnings as error"
	echo "    FILES   Files to check"
	echo "    FOLDERS Folders to check"
	echo "   -h       Print this help"
	echo ""
	echo "Return value:"
	echo "   0 No error"
	echo "   1 Invalid option"
	echo "   2 No check enabled"
	echo "   3 No valid file to check"
	echo "   4 Only Xml files detected and -l option is not enabled"
	echo "   5 Some check failed"
}

function isXml(){
   if [ ${$1: -4} == ".xml" ]; then
      return 0
   else 
      return 1
   fi
}

OPTIND=1 #needed to call the script many times 
while getopts "altsLrhw" opt; do
  case "${opt}" in
    a)
	  CHECK_LINKS=true
      CHECK_HTML=true
      CHECK_SYNTAX=true
      CHECK_LAST=true
	  ;;  
    l)
      CHECK_LINKS=true
      ;;
    t)
	  CHECK_HTML=true
      ;;
	s)
      CHECK_SYNTAX=true
      ;;
	L)
      CHECK_LAST=true
      ;;
	r)
      FIND_RECURSIVELY=true
      ;;
	h)
      usage
	  USAGE_REQ=true
      ;;
	w)
	  W_ERROR=true
	  ;;
    \?)
      echo "Invalid option: -$OPTARG"
	  return 1
      ;;
  esac
done

if [ $CHECK_LINKS = false ] && [ $CHECK_HTML = false ] && [ $CHECK_SYNTAX = false ] && [ $CHECK_LAST = false ]; then
    if [ $USAGE_REQ = false ]; then
      echo "Error: No check was enabled"
      usage
	  return 2
	else
	  usage
	  return 0
	fi
fi

shift $(($OPTIND - 1))

if [ $# -eq 0 ]
then
    FOLDERS="./"
else
	for var in "$@"
	do
		if [[ -d $var ]]; then
			FOLDERS="$FOLDERS $var"
		elif [[ -f $var ]]; then
			FILES="$FILES $var"
		else
			echo "Parameter $var is not valid"
		fi
	done
fi

WARNING_FILES=""
FAILED_FILES=""
MAX_LENGTH_FILE=1

#search html and xml files
for i in $FOLDERS
do
    if [ $FIND_RECURSIVELY = true ]; then
		FILES="$FILES $(find $i -name "*.html")"
		FILES="$FILES $(find $i -name "*.xml")"
	else
		FILES="$FILES $(find $i -maxdepth 1 -name  "*.html")"
		FILES="$FILES $(find $i -maxdepth 1 -name "*.xml")"
	fi
done

#check if no files were found
if [ "$FILES" == "  " ]; then
   echo "Error: No file found to check"
   usage
   return 3
fi

#check if only XML files are present and if -l option is enabled
HTML_FILES="$(echo $FILES | tr ' ' '\n' | grep .html)"
if [ "$HTML_FILES" == "" ]; then
  if [ $CHECK_LINKS = false ]; then
    echo "You are checking only XML files but -l option is not enabled."
    usage
    return 4
  fi 
fi

#check if Warnings as error is enabled
if [ $W_ERROR = true ]
then
	USER_CHECK_COLOR="0;31"
	USER_CHECK_TEXT="FAILED"
fi

for file in $FILES
do
	if (( ${#file} > "$MAX_LENGTH_FILE" )) 
	then
		MAX_LENGTH_FILE=${#file}
	fi
done

for file in $FILES
do
	OK=true
	RESULT=""
	
	if [ $CHECK_LINKS = true ]
	then
		if [ $(basename $file) == "toc.xml" ]
		then
			cat $file | sed 's/toc/a/g' | sed 's/topic=/href=/g' | sed 's/topic/a/g'  | sed '/<?/d' | sed 's,href="html/,href=",g' > testFile.html
			RESULT=$(checklink -s testFile.html | grep "The link is broken" -b3)
			rm testFile.html
		else
			RESULT=$(checklink -s $file | grep "The link is broken" -b3)
		fi
	
		if [ "$RESULT" != "" ]
		then
			OK=false
			printf "\033[0;31m-----------\033[0m\n"
			printf "Checked \033[0;31m%-${MAX_LENGTH_FILE}s FAILED!\033[0m\n" $file
			echo "ERROR: Link error $RESULT"
			printf "\033[0;31m-----------\033[0m\n"
		fi
	fi
	
	if [ $CHECK_HTML = true ] && [ ${file: -5} == ".html" ]
	then
		RESULT=$(curl -H "Content-Type: text/html; charset=utf-8" --data-binary @$file https://validator.w3.org/nu/?out=gnu 2> /dev/null | grep "error\|warning")
		if [ "$RESULT" != "" ]
		then
			OK=false
			printf "\033[0;31m-----------\033[0m\n"
			printf "Checked \033[0;31m%-${MAX_LENGTH_FILE}s FAILED!\033[0m\n" $file
			echo "ERROR HTML: $RESULT"
			printf "\033[0;31m-----------\033[0m\n"
		fi
	fi
	

	if [ $CHECK_SYNTAX = true ] && [ ${file: -5} == ".html" ]
	then
		RESULT=$(cat $file | grep -nE -e "\S"$'\t' -e "[^\-]\->|span class=\"code\"|<i>|<b>|<em>|<strong>|class=\"\"|\S  ") #<span class="code"> is obsolete. <i> and <b> are bad practice. class="" is wrong. And double white space
		if [ "$RESULT" != "" ]
		then
			if [ $W_ERROR = true ]
			then
			OK=false
			else
				WARNING_FILES="$WARNING_FILES $file"
				echo "$WARNING_FILES"
			fi

			printf "\033[0""$USER_CHECK_COLOR""m-----------\033[0m\n"
			printf "Checked \033[""$USER_CHECK_COLOR""m%-${MAX_LENGTH_FILE}s $USER_CHECK_TEXT!\033[0m\n" $file

			RES=$(echo "$RESULT" | grep -E "\S"$'\t')
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Tabs are only allowed at the beginning of the line:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "[^\-]\->")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Don't use arrows like ->, only for comments:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "span class=\"code\"")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Don't use a span with class code. Use inlineCode class or use a div with class code:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "<i>")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Don't use the tag <i>. Put a span aroung it, and use one class that is styled in the css:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "<b>")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Don't use the tag <b>. Put a span aroung it, and use one class that is styled in the css:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "<em>")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Don't use the tag <em>. Put a span aroung it, and use one class that is styled in the css:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "<strong>")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Don't use the tag <strong>. Put a span aroung it, and use one class that is styled in the css:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "class=\"\"")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: The class name is empty.:\n%s\n" "$RES"
			fi

			RES=$(echo "$RESULT" | grep -E "\S  ")
			if [ "$RES" != "" ]
			then
				printf "WARNING AT SYNTAX: Multiple blank space:\n%s\n" "$RES"
			fi
			printf "\n\033[""$USER_CHECK_COLOR""m-----------\033[0m\n"
		fi
	fi
	
	if [ $CHECK_LAST = true ] && [ ${file: -5} == ".html" ]
	then
		RESULT=$(cat $file | grep "<div class=\"lastModified\"><script> document.write(\"This page was last modified on: \" + document.lastModified +\"\");</script></div>") #all pages must have a lastModified division to let the reader know when was the last time the file changed
		if [ "$RESULT" == "" ]
		then
			OK=false
			printf "\033[0;31m-----------\033[0m\n"
			printf "Checked \033[0;31m%-${MAX_LENGTH_FILE}s FAILED!\033[0m\n" $file
			echo "WARNING: Missing lastModified"
			printf "\033[0;31m-----------\033[0m\n"
		fi
	fi
	
	if [ "$OK" = true ]
	then
		printf "Checked %-${MAX_LENGTH_FILE}s \033[0;32mOK!\033[0m\n" $file
	else
		FAILED_FILES="$FAILED_FILES $file"
	fi
done 

if  [ "" != "$WARNING_FILES" ]
then
	printf "\n\033[""$USER_CHECK_COLOR""mWARNING. Check files: $WARNING_FILES.\033[0m\n"
fi

if [ "$FAILED_FILES"  == "" ]
then
	printf "\n\033[0;32mTest SUCCESS. All files are correct.\033[0m\n"
	return 0
else
	printf "\n\033[0;31mTest FAILED. Check files: $FAILED_FILES.\033[0m\n"
	return 5
fi


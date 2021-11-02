#!/bin/sh
rm index.html
echo "<pre>" > index.html
cat README.txt >> index.html
echo "</pre>" >> index.html

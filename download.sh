cd $1
rm urls.txt~
rm urls2.txt~
rm download.sh~
sed -r 's/ /%20/g' urls.txt > urls2.txt
rm urls.txt
sed -r 's/\\//g' urls2.txt > urls.txt
rm urls2.txt
sed -r 's/\|//g' urls.txt > urls2.txt
rm urls.txt
sed -r "s/'//g" urls2.txt > urls.txt
ARQUIVO=urls.txt
declare -i cont=1
cat $ARQUIVO | while read LINHA
do
	cont=$(( cont +1 ))
	eval "wget -O $cont.xml $LINHA"
done
rm urls.txt
rm urls2.txt

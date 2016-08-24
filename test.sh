gradle clean distZip
rm -rf csrtool-1.0
unzip build/distributions/csrtool-1.0.zip

./csrtool-1.0/bin/csrtool create

rm mycsr.csr
./csrtool-1.0/bin/csrtool create --out=mycsr.csr
cat mycsr.csr
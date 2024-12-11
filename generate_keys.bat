echo off
cls

cd src/main/resources

openssl genrsa -out rsa.private 2048

openssl rsa -in rsa.private -out rsa.public -pubout -outform PEM

cd ../../..

echo "Keys generated successfully"
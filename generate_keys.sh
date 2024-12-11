#!/bin/bash

cd src/main/resources;

openssl genrsa -out rsa.private 2048;
openssl rsa -in rsa.private -out rsa.public -pubout -outform PEM;

chmod 755 rsa.private;
chmod 775 rsa.public;

ls -l;

cd ../../..;

echo "Keys generated successfully"; 
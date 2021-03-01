rm example -rf
rm ql-example-db* -rf

/home/hac425/sca/codeql/codeql database create --language=cpp -c "make" ./ql-example-db
/home/hac425/sca/codeql/codeql database bundle -o ql-example-db.zip ql-example-db
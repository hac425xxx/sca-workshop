rm -rf libexample.so
/home/hac425/sca/fortify/bin/sourceanalyzer -b fortify-example make
# /home/hac425/sca/fortify/bin/sourceanalyzer -b fortify-example -scan -f fortify-example.fpr

/home/hac425/sca/fortify/bin/sourceanalyzer -no-default-rules -rules hello.xml -b fortify-example -scan -f fortify-example.fpr

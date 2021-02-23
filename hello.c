#include <stdio.h>
#include <string.h>

char read_byte()
{
    return 1;
}


int example_1()
{
    int x = read_byte();
    char buf[20];
    memset(buf, 0xaa, x);
}


int main()
{
    example_1();
}
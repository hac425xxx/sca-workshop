#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char read_byte()
{
    return 1;
}

// fake get user input function
char* get_user_input_str()
{
    return (char*)malloc(12);
}

int example_1()
{
    int x = read_byte();
    char buf[20];
    memset(buf, 0xaa, x);
}


int clean_data(char* input)
{
    if(strstr(input, "nc") != NULL)
        return 1;
    
    return 0;
}

void our_wrapper_system(char* cmd)
{
    system(cmd);
}


int call_system_example()
{

    char* user = get_user_input_str();

    char* xx = user;

    system(xx);
    return 1;
}


int call_our_wrapper_system_example()
{

    char* user = get_user_input_str();

    char* xx = user;

    our_wrapper_system(xx);
    return 1;
}

int call_system_const_example()
{
    system("cat /etc/xxx");
    return 1;
}


int call_system_safe_example()
{

    char* user = get_user_input_str();

    char* xx = user;

    if(!clean_data(xx))
        return 1;

    system(xx);
    return 1;
}


int main()
{
    call_system_safe_example();
    call_system_example();
    example_1();
}
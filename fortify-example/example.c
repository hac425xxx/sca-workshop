#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

// fake read byte from taint data
char read_byte()
{
    return 1;
}

// fake read int from taint data
int read_int()
{
    return 1;
}

// fake get user input function
char *get_user_input_str()
{
    return (char *)malloc(12);
}

int example_1()
{
    int x = read_byte();
    char buf[20];
    memset(buf, 0xaa, x);
}

int global_array[40] = {0};

void array_oob()
{
    int user = read_byte();
    global_array[user] = 1;
}

void array_oob_2()
{
    int user = read_byte();
    global_array[user + 2] = 1;
}

void no_array_oob()
{
    int user = read_byte();

    if (user >= sizeof(global_array))
        return;

    global_array[user] = 1;
}

void realloc_double_free(char *buf)
{
    int user = read_byte();

    char *rbuf = realloc(buf, user);

    if (rbuf == NULL)
    {
        free(buf);
    }
}

void realloc_no_double_free(char *buf)
{
    int user = read_byte();

    char *rbuf = realloc(buf, user);

    if (user != 0 && rbuf == NULL)
    {
        free(buf);
    }
}

int ref_put(int *k)
{
    *k = *k - 1;
}

int ref_get(int *k)
{
    *k = *k + 1;
}

int ref_leak(int *ref, int a, int b)
{

    ref_get(ref);

    if (a == 2)
    {
        puts("error 2");
        return -1;
    }
    ref_put(ref);
    return 0;
}

int ref_dec_error(int *ref, int a, int b)
{

    ref_get(ref);

    if (a == 2)
    {
        puts("ref_dec_error 2");
        ref_put(ref);
    }
    ref_put(ref);
    return 0;
}

int ref_bug_else(int *ref, int a, int b)
{

    ref_get(ref);

    if (a == 2)
    {
        puts("error 2");
        return -1;
    }
    else
    {
        ref_put(ref);
        return -2;
    }
    ref_put(ref);
    return 0;
}

int ref_no_leak(int *ref, int a, int b)
{

    ref_get(ref);

    if (a == 2)
    {
        puts("error 2");
        ref_put(ref);
        return -1;
    }
    ref_put(ref);
    return 0;
}

int clean_data(char *input)
{
    if (strstr(input, "nc") != NULL)
        return 1;

    return 0;
}

void our_wrapper_system(char *cmd)
{
    system(cmd);
}

int call_system_example()
{

    char *user = get_user_input_str();

    char *xx = user;

    system(xx);
    return 1;
}

int call_our_wrapper_system_example()
{

    char *user = get_user_input_str();

    char *xx = user;

    our_wrapper_system(xx);
    return 1;
}

int call_our_wrapper_system_strcpy_example()
{

    char *user = get_user_input_str();

    char *tmp = malloc(strlen(user) + 1);

    strcpy(tmp, user);

    our_wrapper_system(tmp);
    return 1;
}

int custom_memcpy(char *dst, char *src, int sz);

int call_our_wrapper_system_custom_memcpy_example()
{

    char *user = get_user_input_str();

    char *tmp = malloc(strlen(user) + 1);

    custom_memcpy(tmp, user, strlen(user));

    our_wrapper_system(tmp);
    return 1;
}

int call_system_const_example()
{
    system("cat /etc/xxx");
    return 1;
}

int call_system_safe_example()
{

    char *user = get_user_input_str();

    char *xx = user;

    if (!clean_data(xx))
        return 1;

    system(xx);
    return 1;
}

int system_by_read()
{
    char buf[40];
    read(0, buf, 40);
    
    
    system(buf);
}


int main()
{
    call_system_safe_example();
    call_system_example();
    example_1();
}
#include <ctype.h>
#include <stdio.h>
#include <string.h>

// char one[5][13] = {"list", "end", "sort", "pop", "help", "sqrt" , "rec" ,
// "neg" , "pow" , "+" , "-" , "*" , "/" }; char two[5][4] = {"delete", "search",
// "peek", "push"}; char three[6][2] = {"add", "insert"};

char *strlwr(char *buff) //เปลี่ยนสตริงเป็นพิมพ์เล็กโดยเปลี่ยนทีละตัว macOS มีปัญหาในการใช้
                         //strlwr โดยตรง หากใช้ใน windows ไม่จำเป็นต้องมีส่วนตรงนี้
{
  unsigned char *p = (unsigned char *)buff;
  while (*p) {
    *p = tolower((unsigned char)*p);
    p++;
  }
  return buff;
}

void read_to_array(char *str, char cmd[][20], int *count) {

  char *token;
  char buff[100] = {};

  printf("command> ");
  gets(str);
  strcpy(buff, str);
  strlwr(buff);
  *count = 0;
  token = strtok(buff, " ");
  while (token != NULL) {
    strcpy(cmd[*count], token);
    *count = *count + 1;
    token = strtok(NULL, " ");
  }
}

int check_first_element(char cmd[][20]) {
  int i = 0;
  char cmd_list[20][19] = {"list", "end",  "sort", "pop",    "help",
                           "sqrt", "rec",  "neg",  "pow",    "+",
                           "-",    "*",    "/",    "delete", "search",
                           "peek", "push", "add",  "insert"};

  while ((i < 19) && (strcmp(cmd_list[i], cmd[0]) != 0))
    i++;
  if (i < 13)
    return 1;
  else if (i > 12 && i < 17)
    return 2;
  else if (i > 16 && i < 19)
    return 3;
  return 0;
}

int check_parameter(char cmd[][20], int count, int *parameter) {
  int index = 1;
  int check_int;
  char check_char;

  while ((index < count) &&
         (sscanf(cmd[index], "%d%s", &check_int, &check_char) == 1))
    index++;
  *parameter = index - 1;
  if (*parameter != (count - 1))
    return 0;
  else
    return 1;
}

void check_command(char cmd[][20], int group, int parameter) {
  if (group == 1 && parameter == 0)
    printf("answer> OK\n");
  else if (group == 2 && parameter == 1)
    printf("answer> OK\n");
  else if (group == 3 && parameter >= 1)
    printf("answer> OK\n");
  else
    printf("answer> parameter error\n");
}

int main() {
  int count = 0;
  int group = 0;
  int parameter = 0;
  char str[100] = {};
  char cmd[10][20] = {};
  do {
    read_to_array(str, cmd, &count);
    group = check_first_element(cmd);
    if (group == 0)
      printf("answer> syntax error\n");
    else if (check_parameter(cmd, count, &parameter) == 0)
      printf("answer> parameter error\n");
    else
      check_command(cmd, group, parameter);
  } while (strcmp(str, "end"));

  printf("\nEnd program\n");
  printf("Program wrtiten by 63070501061 Suppakorn Rakna\n");

  return 0;
}

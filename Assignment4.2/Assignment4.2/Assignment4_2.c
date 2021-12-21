#include <ctype.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct linked_list {
  double info;
  struct linked_list *next;
} linkedlist;

linkedlist *first, *last, *ptr;

char *strlwr(char *buff) //เปลี่ยนสตริงเป็นพิมพ์เล็กโดยเปลี่ยนทีละตัว macOS มีปัญหาในการใช้
                         // strlwr โดยตรง หากใช้ใน windows ไม่จำเป็นต้องมีส่วนตรงนี้
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
    strcpy(cmd[(*count)++], token);
    token = strtok(NULL, " ");
  }
}

int check_first_element(char cmd[][20], int *i) {

  *i = 0;
  char cmd_list[20][19] = {"list", "end",  "sort", "pop",    "help",
                           "sqrt", "rec",  "neg",  "pow",    "+",
                           "-",    "*",    "/",    "delete", "search",
                           "peek", "push", "add",  "insert"};

  while ((*i < 19) && (strcmp(cmd_list[*i], cmd[0]) != 0))
    (*i)++;
  if (*i < 13)
    return 1;
  else if (*i > 12 && *i < 17)
    return 2;
  else if (*i > 16 && *i < 19)
    return 3;
  return 0;
}

int check_parameter(char cmd[][20], int count, int *parameter) {

  int index = 1;
  double check_double;
  char check_char;

  while ((index < count) &&
         (sscanf(cmd[index], "%lf%s", &check_double, &check_char) == 1))
    index++;
  *parameter = index - 1;
  if (*parameter != (count - 1))
    return 0;
  else
    return 1;
}

linkedlist *create(double item) {

  linkedlist *ptr;
  ptr = (linkedlist *)malloc(sizeof(linkedlist));

  ptr->next = NULL;
  ptr->info = item;
  return ptr;
}

int linkedlist_size() {

  linkedlist *ptr;
  int count = 0;

  ptr = first;
  while (ptr != NULL) {
    count++;
    ptr = ptr->next;
  }
  return count;
}

void add(double n) {

  linkedlist *ptr = create(n);

  if (first == NULL)
    first = last = ptr;
  else {
    last->next = ptr;
    last = ptr;
  }
}

void push(double item) {

  linkedlist *ptr = create(item);

  if (first == NULL)
    first = last = ptr;
  else {
    ptr->next = first;
    first = ptr;
  }
}

void sort() {

  linkedlist *ptr_i, *ptr_j;
  double x;

  for (ptr_i = first; ptr_i->next != NULL; ptr_i = ptr_i->next)
    for (ptr_j = ptr_i->next; ptr_j != NULL; ptr_j = ptr_j->next)
      if (ptr_j->info < ptr_i->info) {
        x = ptr_i->info;
        ptr_i->info = ptr_j->info;
        ptr_j->info = x;
      }
}

int sorted() {

  linkedlist *ptr = first;
  while (ptr != NULL && ptr->next != NULL && linkedlist_size() > 1) {
    if (ptr->info > ptr->next->info)
      return 0;
    ptr = ptr->next;
  }
  return 1;
}

void insert(double item) {

  int _sorted = sorted();

  if (_sorted == 0)
    printf("answer> can't insert please sort before\n");
  else if (_sorted == 1 || linkedlist_size() == 0) {
    add(item);
    sort();
  }
}

void peek(double n) {

  if (n == -1)
    peek(linkedlist_size() - 1);
  else if (n >= linkedlist_size())
    printf("answer> Maximum peek = %d\n", linkedlist_size() - 1);
  else {
    linkedlist *ptr;
    ptr = first;
    int count = 0;
    double flag = ptr->info;
    while ((ptr != NULL) && (count < n)) {
      ptr = ptr->next;
      flag = ptr->info;
      count++;
    }
    printf("answer> %.0lf\n", flag);
  }
}

void list() {

  int count = 0;
  ptr = first;
  if (ptr == NULL)
    printf("list> NULL\n");
  else {
    printf("list> ");
    while (ptr != NULL) {
      count++;
      printf("%g ", ptr->info);
      ptr = ptr->next;
    }
    printf("\n");
  }
}

int search(double item, int start) {

  linkedlist *ptr;
  int index = 0;
  ptr = first;
  while (index < start && ptr != NULL) {
    ptr = ptr->next;
    index++;
  }
  while ((ptr != NULL) && (ptr->info != item)) {
    ptr = ptr->next;
    index++;
  }
  if (ptr != NULL)
    return index;
  else
    return -1;
}

linkedlist *_delete(int pos) {

  linkedlist *ptr;
  linkedlist *prev = first;
  int i = 0;

  if ((pos == 0) && (linkedlist_size() == 1)) {
    ptr = first;
    first = last = NULL;
  } else if (pos == 0) {
    ptr = first;
    first = first->next;
  } else {
    while (i != pos - 1) {
      prev = prev->next;
      i++;
    }
    if (pos == linkedlist_size()) {
      ptr = last;
      last = prev;
      last->next = NULL;
    } else {
      ptr = prev->next;
      prev->next = ptr->next;
    }
  }
  return ptr;
}

void delete (double item) {

  char ch = '\0';
  int pos = search(item, 0);
  if (pos == -1)
    printf("answer> %.0lf not found\n", item);
  else
    for (int run = 0; run < linkedlist_size() && pos != -1; run++) {
      printf("navigation ");
      list();
      printf("answer> %.0lf found at [%d] enter y to confirm ", item, pos);
      scanf("%s", &ch);
      if (ch == 'y')
        _delete(pos);
      pos = search(item, pos + 1);
    }
}

linkedlist *pop(double *item) {

  linkedlist *ptr = first;
  *item = ptr->info;
  return _delete(0);
}

void help() {

  char cmd_list[20][50] = {"list:   show current stack",
                           "end:    end program",
                           "sort:   ascending sort",
                           "pop:    pop first element",
                           "help:   help menu [using]",
                           "sqrt",
                           "rec",
                           "neg",
                           "pow",
                           "+",
                           "-",
                           "*",
                           "/",
                           "delete <n>",
                           "search <n>",
                           "peek <n>",
                           "push <n>",
                           "add <list>",
                           "insert <list>"};

  printf("answer> list of command\n");
  for (int i = 0; i < 19; i++)
    printf("\t%2d)\t%s\n", i, cmd_list[i]);
}

void unary(int i) {

  double item;
  linkedlist *x = pop(&item);
  double ans = 0.0;

  if (i == 5)
    ans = sqrt(x->info);
  else if (i == 6)
    ans = 1 / (x->info);
  else if (i == 7)
    ans = -(x->info);
  push(ans);
  printf("answer> %.0lf\n", ans);
}

void binary(int i) {

  double item;
  linkedlist *a = pop(&item);
  linkedlist *b = pop(&item);
  double ans = 0.0;

  if (i == 8)
    ans = pow(b->info, a->info);
  else if (i == 9)
    ans = (b->info) + (a->info);
  else if (i == 10)
    ans = (b->info) - (a->info);
  else if (i == 11)
    ans = (b->info) * (a->info);
  else if (i == 12)
    ans = (b->info) / (a->info);
  push(ans);
  printf("answer> %.1lf\n", ans);
}

void group1(int i) {

  double item;

  if (i < 4) {
    if (i == 0)
      list();
    else if (i == 2)
      sort();
    else if (i == 3) {
      pop(&item);
      printf("answer> %.0lf\n", item);
    }
  } else if (i > 4 && i < 8)
    unary(i);
  else if (linkedlist_size() == 1)
    printf("answer> Can't operation\n");
  else
    binary(i);
}

void group2(int i, char cmd[][20]) {

  double n;

  sscanf(cmd[1], "%lf", &n);
  if (i == 13)
    delete (n);
  else if (i == 14) {
    if (search(n, 0) == -1)
      printf("answer> %.0lf not found\n", n);
    else
      printf("answer> found %.0lf at [%d]\n", n, search(n, 0));
  } else if (i == 15)
    peek(n);
  else if (i == 16)
    push(n);
}

void group3(int i, char cmd[][20], int parameter) {

  double n[parameter];
  int j = 0;

  while (j < parameter) {
    sscanf(cmd[j + 1], "%lf", &n[j]);
    j++;
  }
  j = 0;
  if (i == 17)
    while (j < parameter)
      add(n[j++]);
  else if (i == 18)
    while (j < parameter)
      insert(n[j++]);
}

void check_command(char cmd[][20], int group, int parameter, int i) {

  if (group == 1 && parameter == 0)
    if (linkedlist_size() == 0 && i != 1)
      printf("answer> No data\n");
    else
      group1(i);
  else if (group == 2 && parameter == 1) {
    if (linkedlist_size() == 0 && i != 16)
      printf("answer> No data\n");
    else
      group2(i, cmd);
  } else if (group == 3 && parameter >= 1)
    group3(i, cmd, parameter);
  else if (group != 0 && parameter == 0)
    printf("answer> syntax error\n");
  else
    printf("answer> parameter error\n");
}

int main() {

  int i = 0;
  int count = 0;
  int group = 0;
  int parameter = 0;
  char str[100] = {};
  char cmd[10][20] = {};

  do {
    list();
    rewind(stdin);
    read_to_array(str, cmd, &count);
    group = check_first_element(cmd, &i);
    if (i == 4)
      help();
    else if (group == 0)
      printf("answer> syntax error\n");
    else if (check_parameter(cmd, count, &parameter) == 0)
      printf("answer> parameter error\n");
    else
      check_command(cmd, group, parameter, i);
  } while (i != 1);

  printf("\nEnd program\n");
  printf("Program written by 63070501061 Suppakorn Rakna\n\n");

  return 0;
}

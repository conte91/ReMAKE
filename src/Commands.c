#include <xdo.h>
#include <stdio.h>

xdo_t* tool;
void initTool(){
  tool=xdo_new(NULL);
  if(!tool){
    fprintf(stderr, "This is not funny, Jack\n");
  }
}

void MoveMouse(int x, int y){
  if(!tool){
    fprintf(stderr, "Attempting to use empty XDoTool");
    return;
  }
  if(xdo_mousemove(tool, x,y,0)){
    fprintf(stderr, "Faaaaail\n");
  }
}

void MoveMouseRelative(int x, int y){
  if(!tool){
    fprintf(stderr, "Attempting to use empty XDoTool");
    return;
  }
  if(xdo_mousemove_relative(tool, x, y)){
    fprintf(stderr, "Faaaail\n");
  }
}

void LeftClick(){
  if(!tool){
    fprintf(stderr, "Attempting to use empty XDoTool");
    return;
  }
  if(xdo_click 	(tool, CURRENTWINDOW, 1)){
    fprintf(stderr, "Click faaaail\n");
  }
}

void doClick(int button){
  if(!tool){
    fprintf(stderr, "Attempting to use empty XDoTool");
    return;
  }

  if(xdo_click 	(tool, CURRENTWINDOW, button)){
    fprintf(stderr, "Click faaaail\n");
  }
}

void sendKeys(const char* string, int newline){
  if(!tool){
    fprintf(stderr, "Attempting to use empty XDoTool");
    return;
  }

  if(xdo_type(tool, CURRENTWINDOW, string, 10000) || (newline && xdo_type(tool, CURRENTWINDOW, "\n", 10000))){
    fprintf(stderr, "Keyboard faaaaail\n");
  }
}

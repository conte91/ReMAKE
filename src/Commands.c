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


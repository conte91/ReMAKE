#include "TcpServer.h"
#include "Commands.h"

int main() {
  initTool();
  try
  {
    boost::asio::io_service io_service;
    TcpServer server(io_service);
    io_service.run();
  }
  catch (std::exception& e)
  {
    std::cerr << e.what() << std::endl;
  }

  return 0;
/*

  if(xdo_mousemove(tool, 5,5,0)){
    fprintf(stderr, "Faaaaail\n");
  }
  if(xdo_mousedown(tool, CURRENTWINDOW, 1) || xdo_mouseup(tool, CURRENTWINDOW, 1)){
    fprintf(stderr, "Faaaail\n");
  }
  return 0;
  */
}

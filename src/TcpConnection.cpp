#include "TcpConnection.h"
#include "Commands.h"

TcpConnection::Pointer TcpConnection::create(boost::asio::io_service& io_service)
{
  return Pointer(new TcpConnection(io_service));
}

boost::asio::ip::tcp::socket& TcpConnection::socket()
{
  return _socket;
}

void TcpConnection::start()
{

  boost::asio::async_read(_socket, boost::asio::buffer(_message, 65536), boost::asio::transfer_at_least(1), 
      boost::bind(&TcpConnection::handleRead, shared_from_this(), boost::asio::placeholders::error, boost::asio::placeholders::bytes_transferred));
}
  TcpConnection::TcpConnection(boost::asio::io_service& io_service)
: _socket(io_service)
{
  std::cout << "Creating Connection" << std::endl;
}

TcpConnection::~TcpConnection(){
  std::cout << "Goodbye, cruel world" << std::endl;
}
void TcpConnection::handleWrite(const boost::system::error_code& /*error*/,
    size_t /*bytes_transferred*/)
{
}

void TcpConnection::handleRead(const boost::system::error_code& error, size_t bytes_transferred){
  if(!error){
    _message[bytes_transferred]=0;
    _bufMutex.lock();
    _readBuffer << _message;
    _bufMutex.unlock();
    boost::asio::async_read(_socket, boost::asio::buffer(_message), boost::asio::transfer_at_least(1), 
      boost::bind(&TcpConnection::handleRead, shared_from_this(), boost::asio::placeholders::error, boost::asio::placeholders::bytes_transferred));
    parseBuffer();
  }
}

void TcpConnection::parseBuffer(){
  _bufMutex.lock();
  while(_readBuffer.str().find('\n', _readBuffer.tellg())!=std::string::npos){
    char command;
    /** We've got a complete line */
    std::string line;
    std::getline(_readBuffer, line);
    std::stringstream parse(line);
    parse >> command;
    if(!parse.fail()){
      int x, y;
      switch(command){
        case 'M':
          parse >> x >> y;
          if(!parse.fail()){
            MoveMouse(x, y);
          }
          break;
        case 'C':
          parse >> x >> y;
          for(int i=0; i<x; ++i){
            doClick(y);
          }
          break;
        case 'R':
          parse >> x >> y;
          if(!parse.fail()){
            MoveMouseRelative(x, y);
          }
          break;
        case 'T':
          parse >> x;
          if(!parse.fail()){
            /** Reads until EOL */
            line.erase(0, parse.tellg());
            sendKeys(line.c_str(), x);
          }
          break;
      }
    }
  }
  /** Delete read chars from string */
  std::string banana=_readBuffer.str();
  int upToWhat=_readBuffer.tellg();
  banana.erase(0, upToWhat);
  _readBuffer.str(banana);
  _bufMutex.unlock();
}

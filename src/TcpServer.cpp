#include "TcpServer.h"
  TcpServer::TcpServer(boost::asio::io_service& io_service)
: _acceptor(io_service, boost::asio::ip::tcp::endpoint(boost::asio::ip::tcp::v4(), 9999))
{
  startAccept();
}

void TcpServer::startAccept()
{
  TcpConnection::Pointer new_connection =
    TcpConnection::create(_acceptor.get_io_service());

  _acceptor.async_accept(new_connection->socket(), boost::bind(&TcpServer::handleAccept, this, new_connection, boost::asio::placeholders::error));
}

void TcpServer::handleAccept(TcpConnection::Pointer new_connection, const boost::system::error_code& error)
{
  if (!error)
  {
    new_connection->start();
    startAccept();
  }
}

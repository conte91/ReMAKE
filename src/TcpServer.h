
#include <ctime>
#include <iostream>
#include <string>
#include <boost/bind.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include <boost/asio.hpp>
#include "TcpConnection.h"
class TcpServer
{
  public:
    TcpServer(boost::asio::io_service& io_service);

  private:
    void startAccept();
    void handleAccept(TcpConnection::Pointer new_connection, const boost::system::error_code& error);

    boost::asio::ip::tcp::acceptor _acceptor;
};

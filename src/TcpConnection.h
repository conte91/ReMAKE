
#include <iostream>
#include <string>
#include <boost/bind.hpp>
#include <boost/shared_ptr.hpp>
#include <boost/enable_shared_from_this.hpp>
#include <boost/signals2/mutex.hpp>
#include <boost/asio.hpp>
class TcpConnection
: public boost::enable_shared_from_this<TcpConnection>
{
  public:
    typedef boost::shared_ptr<TcpConnection> Pointer;

    static Pointer create(boost::asio::io_service& io_service);

    boost::asio::ip::tcp::socket& socket();
    void start();
    ~TcpConnection();
  private:
    TcpConnection(boost::asio::io_service& io_service);
    void handleWrite(const boost::system::error_code& error, size_t bytes_transferred);
    void handleRead(const boost::system::error_code& error, size_t bytes_transferred);
    void parseBuffer();
    boost::asio::ip::tcp::socket _socket;
    boost::signals2::mutex _bufMutex;
    char _message[65536];
    std::stringstream _readBuffer;
};

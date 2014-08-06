FIND_PACKAGE( Boost COMPONENTS system REQUIRED)
add_executable(tcpServer TcpServer.cpp TcpConnection.cpp main.cpp Commands.c)
INCLUDE_DIRECTORIES(${Boost_INCLUDE_DIRS})
target_link_libraries(tcpServer ${Boost_LIBRARIES} xdo)

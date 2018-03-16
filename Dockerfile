FROM java:8

RUN apt-get update && \
    apt-get install -y build-essential make pkg-config libtool autoconf automake curl

RUN cd /opt && \
    curl http://download.zeromq.org/zeromq-4.0.5.tar.gz -L -o /opt/zeromq-4.0.5.tar.gz && \
    tar -zxf zeromq-4.0.5.tar.gz && \
    rm zeromq-4.0.5.tar.gz && \
    cd /opt/zeromq-4.0.5 && \
    ./configure && \
    make && \
    make install && \
    ldconfig && \
    cd /opt && \
    rm -rf zeromq-4.0.5

RUN ln -s /usr/bin/libtoolize /usr/bin/libtool

RUN curl -L https://github.com/zeromq/jzmq/archive/v3.1.0.tar.gz -o /opt/jzmq-3.1.0.tar.gz && \
    cd /opt && tar -zxf jzmq-3.1.0.tar.gz && \
    rm jzmq-3.1.0.tar.gz && \
    cd /opt/jzmq-3.1.0 && \
    ./autogen.sh && \
    ./configure && \
    make && \
    make install && \
    ldconfig && \
    cd /opt

ENV LD_LIBRARY_PATH /usr/local/lib
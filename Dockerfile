From pub.inner.focus.cn/focus/focus-jetty8:0.0.6
COPY . /home/work/code
WORKDIR /home/work/code
RUN chown -R work:work /home/work
USER work
#VOLUME /home/work/data
RUN source ~/.bash_profile && mvn clean package -DskipTests=true 
RUN rm -rf /home/work/focus-jetty/webapps/* && mv  target/app-src/* /home/work/focus-jetty/webapps/ && cd /home/work/focus-jetty/webapps/ && mv app/* ./ && cp conf/* ../conf/
CMD sh /home/work/focus-jetty/sbin/domeos_control

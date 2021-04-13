FROM nginx:1.11.6
MAINTAINER maximiliano.baez@konecta.com.py


RUN apt-get  update && apt-get -o Dpkg::Options::="--force-confnew" upgrade -y
RUN apt-get -y install build-essential git nodejs npm nodejs-legacy wget

# Se actualiza npm
RUN npm cache clean -f && npm install -g n && n stable
RUN npm install -g grunt-cli
RUN npm install -g yarn
#Se copia el codigo fuente y se compila
RUN rm -r /usr/share/nginx/html/*
COPY ./ /usr/share/nginx/html
RUN cd /usr/share/nginx/html; yarn install

#RUN cp -r /app/web/dist /usr/share/nginx/html

COPY resources/default.conf /etc/nginx/conf.d/default.conf
COPY resources/nginx.conf /etc/nginx/nginx.conf

WORKDIR /usr/share/nginx/html

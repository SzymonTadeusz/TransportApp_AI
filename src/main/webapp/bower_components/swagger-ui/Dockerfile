FROM alpine:3.3

MAINTAINER Roman Tarnavski

RUN apk add --update nginx

COPY nginx.conf /etc/nginx/
ADD ./dist/ /usr/share/nginx/html

EXPOSE 8081

CMD nginx -g 'daemon off;'

# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install --production # 반드시 run npm install이 먼저나와야한다 이유?
RUN npm install -g @vue/cli
RUN npm install axios --save   #vue에 axios 설치
RUN npm install --save @popperjs/core #bootstrap설치이전필요
RUN npm install vue bootstrap bootstrap-vue-3 #bootstrap설치
COPY . .
RUN npm run build

# production stage
FROM nginx:stable-alpine as production-stage
RUN rm /etc/nginx/conf.d/default.conf
RUN rm /etc/nginx/nginx.conf
COPY ./nginxconfig/nginx.conf /etc/nginx/
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
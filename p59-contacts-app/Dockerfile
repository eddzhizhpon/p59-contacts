#Primera etapa
FROM node as build-step

RUN mkdir -p /app

WORKDIR /app

COPY package.json /app

RUN npm install

COPY . /app

RUN npm run build --prod

#Segunda Etapa
FROM nginx

COPY --from=build-step /app/dist/p59-contacts-app /usr/share/nginx/html

EXPOSE 80
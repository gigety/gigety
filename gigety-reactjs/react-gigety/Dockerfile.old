# Build Phase Builds our production Reactsjs app
FROM node:alpine as builder
WORKDIR /app
COPY . /app
ENV PATH /app/node_modules/.bin:$PATH
RUN yarn
RUN yarn build

# Run Phase Creates The NGINX Server
FROM nginx:alpine
# Copies code from above to NGINX static html dir
COPY --from=builder /app/build /usr/share/nginx/html
# Remove default config
RUN rm /etc/nginx/conf.d/default.conf
# Replace with custom configuration
COPY nginx/nginx.conf /etc/nginx/conf.d

# OPEN PORT 80 and Start NGINX
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

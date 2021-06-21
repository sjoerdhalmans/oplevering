import { Logger } from '@nestjs/common';
import { NestFactory } from '@nestjs/core';
import { MicroserviceOptions, Transport } from '@nestjs/microservices';
import { AppModule } from './app.module';

const logger = new Logger('Main');

const microserviceOptions = {
  transport: Transport.REDIS,
  options: {
    host: '127.0.0.1'
  },
} as MicroserviceOptions

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  app.connectMicroservice<MicroserviceOptions>(microserviceOptions)
  app.startAllMicroservices();
  app.listen(3500);
  logger.log('Microservice is listening... ');
}
bootstrap();

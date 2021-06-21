import { Body, Controller, Get, Logger, Param, Post } from '@nestjs/common';
import { AppService } from './app.service';
import { MessagePattern } from '@nestjs/microservices'
import { throws } from 'node:assert';
import { get } from 'node:http';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) { }

  private logger = new Logger('AppController');
  //tes
  @MessagePattern('post_order')
  async PostOrder() {
  }
}

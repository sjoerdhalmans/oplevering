import { Body, Controller, Get, Logger, Param, Post } from '@nestjs/common';
import { AppService } from './app.service';
import { MessagePattern } from '@nestjs/microservices'
import { UpdatePassword } from './DTO\'s/password.dto';
import { throws } from 'node:assert';
import { assignRoles, UpdateUser } from './DTO\'s/user.dto';
import { get } from 'node:http';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) { }

  private logger = new Logger('AppController');
  //tes
  @MessagePattern('updatePassword')
  async updatePassword(@Body() passwordDto: UpdatePassword) {
    this.appService.updatePassword(passwordDto);
  }

  @MessagePattern('updateUser')
  async updateUser(@Body() userDto: UpdateUser) {
    this.appService.updateUser(userDto);
  }

  @MessagePattern('assignRoles')
  async assignRoles(@Body() roleDto: assignRoles) {
    this.appService.assignRoles(roleDto);
  }

  @MessagePattern('removeRoles')
  async removeRoles(@Body() roleDto: assignRoles) {
    this.appService.removeRoles(roleDto);
  }

  @Get('/getall')
  async getAll(): Promise<any[]> {
    return await this.appService.getAll();
  }

  @Get('/userroles/:id')
  async getUserRoles(@Param('id') id: string): Promise<any[]> {
    return await this.appService.getUserRoles(id);
  }

  @Get('/roles')
  async getRoles(): Promise<any[]> {
    return await this.appService.getRoles();
  }

  @MessagePattern('ping_me')
  async PING() {
    this.logger.log('pong')
    return 2
  }

  @Get('/token')
  async Token() {
    console.log(await this.appService.getToken());
  }
}

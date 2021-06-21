import { Body, Controller, Get, Logger } from '@nestjs/common';
import { MessagePattern } from '@nestjs/microservices'
import { createRequisition } from "src/DTO's/requisition.dto";
import { RequisitionService } from './requisition.service';

@Controller()
export class RequisitionController {
    constructor(private readonly requisitionService: RequisitionService) { }

    private logger = new Logger('OrderController');
    //tes
    @MessagePattern('createRequisition')
    async createRequisition(@Body() requisitionDto: createRequisition) {
        this.requisitionService.createRequisition(requisitionDto);
    }

    @MessagePattern('deleteRequisition')
    async deleteRequisition(@Body() requisitionId: Number) {
        this.requisitionService.deleteRequisition(requisitionId);
    }

    @Get('/getAllRequisitions')
    async getAll() {
        return this.requisitionService.getAllRequisitions();
    }
}


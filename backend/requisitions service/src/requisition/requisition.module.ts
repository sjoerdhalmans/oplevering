import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Requisition } from 'src/entities/requisition.entity';
import { RequisitionController } from './requisition.controller';
import { RequisitionService } from './requisition.service';

@Module({
    imports: [TypeOrmModule.forFeature([Requisition])],
    controllers: [RequisitionController],
    providers: [RequisitionService],
})
export class RequisitionModule {}
import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { createRequisition } from "src/DTO's/requisition.dto";
import { Requisition } from "src/entities/requisition.entity";
import { Repository } from "typeorm";


@Injectable()
export class RequisitionService {
    constructor(
        @InjectRepository(Requisition)
        private requisitionRepository: Repository<Requisition>,
    ) { }

    public async createRequisition(requisitionDto: createRequisition) {
        const date = new Date()
        const requisition = new Requisition(
            date,
            requisitionDto.OrderedItemType,
            requisitionDto.OrderedItemId,
            requisitionDto.Quantity,
            requisitionDto.Requester,
            requisitionDto.Department
        )

        this.requisitionRepository.save(requisition);
    }

    public async deleteRequisition(requisitionId) {
        this.requisitionRepository.delete(requisitionId)
    }

    public async getAllRequisitions() {
        const requisitions = await this.requisitionRepository.find();

        return requisitions;
    }
}
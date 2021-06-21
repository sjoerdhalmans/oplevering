import { Injectable } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { adjustInventoryItem } from "src/DTO's/inventoryItem.dto";
import { InventoryItem } from "src/entities/inventoryItem.entity";
import { Repository } from "typeorm";


@Injectable()
export class InventoryService {
    constructor(
        @InjectRepository(InventoryItem)
        private inventoryRepository: Repository<InventoryItem>,
    ) { }

    public async addInventoryItem(inventoryItemDto: adjustInventoryItem) {
        const items = await this.inventoryRepository.find()

        if (items.length > 0 && await this.findItemById(inventoryItemDto, items)) {
            let item = await this.findItemById(inventoryItemDto, items)

            console.log(typeof parseInt(inventoryItemDto.quantity.toString()))

            item.quantity = parseInt(item.quantity) + parseInt(inventoryItemDto.quantity.toString());
            
            this.inventoryRepository.save(item);
        }
        else {
            this.saveInventoryItemDto(inventoryItemDto);
        }
    }

    public async removeInventoryItem(inventoryItemDto: adjustInventoryItem) {
        const items = await this.inventoryRepository.find()

        let item = await this.findItemById(inventoryItemDto, items)

        item.quantity = parseInt(item.quantity) - parseInt(inventoryItemDto.quantity.toString());
        this.inventoryRepository.save(item);
    }

    private async findItemById(inventoryItemDto: adjustInventoryItem, items: InventoryItem[]) {
        let foundRecord;

        await items.forEach(item => {
            if (item.itemId == inventoryItemDto.itemId && inventoryItemDto.type == item.type) {
                foundRecord = item;
            }
        });

        return foundRecord
    }

    private saveInventoryItemDto(inventoryItemDto: adjustInventoryItem) {
        let item = new InventoryItem(inventoryItemDto.type, inventoryItemDto.itemId, inventoryItemDto.quantity);

        this.inventoryRepository.save(item);
    }

    public async getMaterials() {
        const storedMaterials = await this.inventoryRepository.find({ type: 'Material' });

        return storedMaterials;
    }

    public async getAllItems() {
        const storedItems = await this.inventoryRepository.find();

        return storedItems;
    }
}

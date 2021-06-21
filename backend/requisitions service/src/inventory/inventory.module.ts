import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { InventoryItem } from 'src/entities/inventoryItem.entity';
import { InventoryController } from './inventory.controller';
import { InventoryService } from './inventory.service';

@Module({
    imports: [TypeOrmModule.forFeature([InventoryItem])],
    controllers: [InventoryController],
    providers: [InventoryService],
})
export class InventoryModule {}

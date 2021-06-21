import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity({name: 'inventory'})
export class InventoryItem {

  constructor(type, itemId, quantity) {
    this.type = type;
    this.itemId = itemId;
    this.quantity = quantity;
  }

  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  type: string;

  @Column()
  itemId: number;

  @Column()
  quantity: number;
}

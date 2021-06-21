import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Requisition {
  constructor(created, orderedItemType, orderedItemId, quantity, requester, department) {
    this.created = created
    this.orderItemType = orderedItemType
    this.orderedItemId = orderedItemId
    this.quantity = quantity
    this.requester = requester
    this.department = department
  }

  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  created: Date;

  @Column()
  orderItemType: String;

  @Column()
  orderedItemId: Number;

  @Column()
  quantity: Number;

  @Column()
  requester: string;

  @Column()
  department: string
}

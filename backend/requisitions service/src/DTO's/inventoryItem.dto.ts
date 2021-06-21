import { IsNumber, IsString } from 'class-validator';

export class adjustInventoryItem {
  @IsString()
  type: string;

  @IsNumber()
  itemId: number;

  @IsNumber()
  quantity: number;
}

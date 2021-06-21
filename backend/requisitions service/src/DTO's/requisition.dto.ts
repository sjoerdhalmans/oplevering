import { Entity, Column, PrimaryGeneratedColumn, Long } from 'typeorm';
import { IsArray, IsDate, IsEnum, IsNumber, IsString } from 'class-validator';

export class createRequisition {
    @IsString()
    OrderedItemType: string;

    @IsNumber()
    OrderedItemId: Number;

    @IsNumber()
    Quantity: Long;

    @IsString()
    Requester: string;

    @IsString()
    Department: string
}

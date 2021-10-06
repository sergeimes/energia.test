import { ChangeItem } from "@app/_models/change-item";

export class CheckoutResponse {
  paid: number;
  change: number;
  changes: ChangeItem[];
}

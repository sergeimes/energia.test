import { Component } from "@angular/core";
import { MessageService } from "@app/_services/message.service";

@Component({
  selector: "app-cofeteria-message",
  templateUrl: "./cofeteria-message.component.html",
  styleUrls: ["./cofeteria-message.component.less"],
  host: { "[class.ngb-toasts]": "true" }
})
export class CofeteriaMessageComponent {
  constructor(public messageService: MessageService) {
  }
}

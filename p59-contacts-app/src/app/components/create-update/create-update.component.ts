import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Contact } from '@domain/contact';
import { ContactRestService } from '@services/contact-rest.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-create-update',
  templateUrl: './create-update.component.html',
  styleUrls: ['./create-update.component.scss']
})
export class CreateUpdateComponent implements OnInit {

  @Input("contact")
  contact: Contact;

  @Output("onSaved")
  savedEmit: EventEmitter<void> = new EventEmitter<void>();

  constructor(private contactRestService: ContactRestService) { }

  ngOnInit(): void {
    if (!this.contact) this.contact = new Contact(); 
  }

  saveContact(): void {
    if (this.contact.id && this.contact.id !== 0) {
      this.contactRestService.update(this.contact).subscribe( (contact: Contact) => {
        this.savedEmit.emit();
      });
    } else {
      this.contactRestService.create(this.contact).subscribe((contact: Contact) => {
        this.savedEmit.emit();
      });
    }
    
  }

}

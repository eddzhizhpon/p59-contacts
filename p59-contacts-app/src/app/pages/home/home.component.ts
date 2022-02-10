import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { CreateUpdateComponent } from '@components/create-update/create-update.component';
import { ListComponent } from '@components/list/list.component';
import { Contact } from '@domain/contact';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
  
})
export class HomeComponent implements OnInit {

  @ViewChild('listComponent')
  listComponent: ListComponent;

  contactSelected: Contact;

  constructor() { }

  ngOnInit(): void {
    this.contactSelected = new Contact();
  }

  updateContact(contact: Contact): void {
    this.contactSelected = contact;
  }

  async onSavedContact(event: void): Promise<void> {
    await this.listComponent.reloadList();
    this.contactSelected = new Contact();
  }

}

import { Component, EventEmitter, Input, OnInit, Output, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from '@domain/contact';
import { ContactRestService } from '@services/contact-rest.service';
import { Observable, Subject, throwError } from 'rxjs';
import { catchError, startWith, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {

  @Output('contactUpdate')
  updateContactEmit = new EventEmitter<Contact>();
  
  contactList: Contact[];

  readonly contactList$: Observable<Contact[]>;
  private readonly refreshClick$: Subject<void> = new Subject<void>();

  constructor(
    private contactRestService: ContactRestService
  ) {
    this.contactList$ = this.refreshClick$.pipe(
      startWith(undefined),
      switchMap( () => this.contactRestService.list() )
    );
  }
  ngOnDestroy(): void {
    this.refreshClick$.complete();
  }

  ngOnInit(): void {
    this.reloadList();
  }

  updateContact(contact: Contact): void {
    let contactSelected: Contact = new Contact()
    contactSelected.id = contact.id;
    contactSelected.fullName = contact.fullName;
    contactSelected.number = contact.number;
    contactSelected.address = contact.address;

    this.updateContactEmit.emit(contactSelected);
  }

  deleteContact(contact: Contact): void {
    this.contactRestService.delete(contact).subscribe( (response) => {
      this.reloadList();
    });
  }

  reloadList(): void {
    this.refreshClick$.next();
  }
}

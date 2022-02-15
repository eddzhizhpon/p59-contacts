import { HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from '@domain/contact';
import { ContactRestService } from '@services/contact-rest.service';
import { Observable, of, Subject, throwError } from 'rxjs';
import { catchError, startWith, switchMap, endWith, tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment.prod';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {

  @Output('contactUpdate')
  updateContactEmit = new EventEmitter<Contact>();
  
  contactList: Contact[];
  downloadUrl = environment.wsUrl + '/download';

  readonly contactList$: Observable<Contact[]>;
  private readonly refreshClick$: Subject<void> = new Subject<void>();

  loading: boolean;

  error: any;

  constructor(
    private contactRestService: ContactRestService
  ) {
    this.loading = true;
    this.contactList$ = this.refreshClick$.pipe(
      startWith(undefined),
      switchMap(() => this.contactRestService.list()),
      tap( () => this.loading = false),
      
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
    this.error = undefined;
    this.loading = true;
    this.refreshClick$.next();
  }
}

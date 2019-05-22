import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ReportGeneratorComponent} from './report-generator.component';
import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-vote-for',
    template: ''
}) // tslint:disable-next-line:component-class-suffix
export class VoteComponentStub {
    @Input() feature: string;
};

describe('ReportGeneratorComponent', () => {
    let component: ReportGeneratorComponent;
    let fixture: ComponentFixture<ReportGeneratorComponent>;

    beforeEach(async(() => {
        // noinspection JSIgnoredPromiseFromCall
        TestBed.configureTestingModule({
                   declarations: [ReportGeneratorComponent, VoteComponentStub],
               })
               .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ReportGeneratorComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});

import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PersonComponentsPage from './person.page-object';
import PersonUpdatePage from './person-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Person e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personComponentsPage: PersonComponentsPage;
  let personUpdatePage: PersonUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    personComponentsPage = new PersonComponentsPage();
    personComponentsPage = await personComponentsPage.goToPage(navBarPage);
  });

  it('should load People', async () => {
    expect(await personComponentsPage.title.getText()).to.match(/People/);
    expect(await personComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete People', async () => {
    const beforeRecordsCount = (await isVisible(personComponentsPage.noRecords)) ? 0 : await getRecordsCount(personComponentsPage.table);
    personUpdatePage = await personComponentsPage.goToCreatePerson();
    await personUpdatePage.enterData();

    expect(await personComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(personComponentsPage.table);
    await waitUntilCount(personComponentsPage.records, beforeRecordsCount + 1);
    expect(await personComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await personComponentsPage.deletePerson();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(personComponentsPage.records, beforeRecordsCount);
      expect(await personComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(personComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

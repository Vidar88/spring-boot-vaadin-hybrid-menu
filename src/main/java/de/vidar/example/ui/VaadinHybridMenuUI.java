package de.vidar.example.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.vidar.example.ui.view.GroupPage;
import de.vidar.example.ui.view.HomePage;
import de.vidar.example.ui.view.MemberPage;
import de.vidar.example.ui.view.SettingsPage;
import de.vidar.example.ui.view.ThemeBuilderPage;
import kaesdingeling.hybridmenu.HybridMenu;
import kaesdingeling.hybridmenu.builder.HybridMenuBuilder;
import kaesdingeling.hybridmenu.builder.NotificationBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuSubMenuBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuLabelBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuSubContentBuilder;
import kaesdingeling.hybridmenu.components.NotificationCenter;
import kaesdingeling.hybridmenu.data.DesignItem;
import kaesdingeling.hybridmenu.data.MenuConfig;
import kaesdingeling.hybridmenu.data.enums.EMenuComponents;
import kaesdingeling.hybridmenu.data.enums.EMenuStyle;
import kaesdingeling.hybridmenu.data.enums.ENotificationPriority;
import kaesdingeling.hybridmenu.data.leftmenu.MenuButton;
import kaesdingeling.hybridmenu.data.leftmenu.MenuSubMenu;
import kaesdingeling.hybridmenu.data.top.TopMenuButton;
import kaesdingeling.hybridmenu.data.top.TopMenuLabel;
import kaesdingeling.hybridmenu.data.top.TopMenuSubContent;

@SpringUI
@Theme("mytheme")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("HybridMenu Spring Boot Test")
@SuppressWarnings("serial")
public class VaadinHybridMenuUI extends UI {

	private final SpringViewProvider viewProvider;

	private HybridMenu hybridMenu = null;
	private NotificationCenter notificationCenter = null;

	public VaadinHybridMenuUI(SpringViewProvider viewProvider) {
		this.viewProvider = viewProvider;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		UI.getCurrent().setPollInterval(5000);

		MenuConfig menuConfig = new MenuConfig();
		menuConfig.setDesignItem(DesignItem.getDarkDesign());

		this.notificationCenter = new NotificationCenter(5000);

		this.hybridMenu = HybridMenuBuilder.get()
				.setContent(new VerticalLayout())
				.setMenuComponent(EMenuComponents.LEFT_WITH_TOP)
				.setConfig(menuConfig)
				.withNotificationCenter(this.notificationCenter)
				.build();

		// Define the TopMenu in this method
		buildTopMenu(this.hybridMenu);

		// Define the LeftMenu in this method
		buildLeftMenu(this.hybridMenu);

		getNavigator().addProvider(this.viewProvider);
		setContent(this.hybridMenu);

		// Change default view here
		getNavigator().navigateTo(HomePage.VIEW_NAME);
	}

	private void buildTopMenu(HybridMenu hybridMenu) {

		TopMenuButtonBuilder.get()
				.setCaption("Home")
				.setIcon(VaadinIcons.HOME)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.setNavigateTo(HomePage.class)
				.build(hybridMenu);

		TopMenuButtonBuilder.get()
				.setCaption("Member")
				.setIcon(VaadinIcons.USER)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.setHideCaption(false)
				.setNavigateTo(MemberPage.class)
				.build(hybridMenu);

		TopMenuButtonBuilder.get()
				.setCaption("Member")
				.setIcon(VaadinIcons.USER)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.setHideCaption(false)
				.addStyleName(EMenuStyle.ICON_RIGHT)
				.setNavigateTo(MemberPage.class)
				.build(hybridMenu);

		TopMenuSubContent userAccountMenu = TopMenuSubContentBuilder.get()
				.setButtonCaption("Test User")
				.setButtonIcon(new ThemeResource("images/profilDummy.jpg"))
				.addButtonStyleName(EMenuStyle.ICON_RIGHT)
				.addButtonStyleName(EMenuStyle.PROFILVIEW)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.build(hybridMenu);

		userAccountMenu.addLabel("Account");
		userAccountMenu.addHr();
		userAccountMenu.addButton("Test");
		userAccountMenu.addHr();
		userAccountMenu.addButton("Test 2");

		TopMenuButtonBuilder.get()
				.setCaption("Home")
				.setIcon(VaadinIcons.HOME)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.setToolTip("5")
				.setNavigateTo(HomePage.class)
				.build(hybridMenu);

		TopMenuButton notiButton = TopMenuButtonBuilder.get()
				.setIcon(VaadinIcons.BELL_O)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.build(hybridMenu);

		this.notificationCenter.setNotificationButton(notiButton);

		TopMenuLabel label = TopMenuLabelBuilder.get()
				.setCaption("<b>Hybrid</b> Menu")
				.setIcon(new ThemeResource("images/hybridmenu-Logo.png"))
				.build(hybridMenu);

		label.getComponent().addClickListener(e -> {
			UI.getCurrent().getNavigator().navigateTo(HomePage.class.getSimpleName());
		});

		TopMenuButton notiButtonLow = TopMenuButtonBuilder.get()
				.setCaption("Add Low notification")
				.setIcon(VaadinIcons.BELL_O)
				.setUseOwnListener(true)
				.build(hybridMenu);

		TopMenuButton notiButtonMedium = TopMenuButtonBuilder.get()
				.setCaption("Add Medium notification")
				.setIcon(VaadinIcons.BELL_O)
				.setUseOwnListener(true)
				.build(hybridMenu);

		TopMenuButton notiButtonHigh = TopMenuButtonBuilder.get()
				.setCaption("Add High notification")
				.setIcon(VaadinIcons.BELL_O)
				.setUseOwnListener(true)
				.build(hybridMenu);

		notiButtonLow.addClickListener(e -> {
			NotificationBuilder.get(this.notificationCenter)
					.withCaption("Test")
					.withDescription("This is a LOW notification")
					.withPriority(ENotificationPriority.LOW)
					.withCloseButton()
					.build();
		});

		notiButtonMedium.addClickListener(e -> {
			NotificationBuilder.get(this.notificationCenter)
					.withCaption("Test")
					.withDescription("This is a MEDIUM notification")
					.build();
		});

		notiButtonHigh.addClickListener(e -> {
			NotificationBuilder.get(this.notificationCenter)
					.withCaption("Test")
					.withDescription("This is a HIGH notification")
					.withPriority(ENotificationPriority.HIGH)
					.withIcon(VaadinIcons.INFO)
					.withCloseButton()
					.build();
		});


		TopMenuButtonBuilder.get()
				.setCaption("Home")
				.setIcon(VaadinIcons.HOME)
				.setNavigateTo(HomePage.class)
				.build(hybridMenu);

	}

	private void buildLeftMenu(HybridMenu hybridMenu) {
		MenuButton homeButton = LeftMenuButtonBuilder.get()
				.withCaption("Home")
				.withIcon(VaadinIcons.HOME)
				.withNavigateTo(HomePage.class)
				.build();

		hybridMenu.addLeftMenuButton(homeButton);

		MenuButton themeBuilderButton = LeftMenuButtonBuilder.get()
				.withCaption("Theme Builder")
				.withIcon(FontAwesome.WRENCH)
				.withNavigateTo(ThemeBuilderPage.class)
				.build();

		hybridMenu.addLeftMenuButton(themeBuilderButton);

		MenuButton settingsButton = LeftMenuButtonBuilder.get()
				.withCaption("Settings")
				.withIcon(VaadinIcons.COGS)
				.withNavigateTo(SettingsPage.class)
				.build();

		hybridMenu.addLeftMenuButton(settingsButton);



		MenuSubMenu memberList = LeftMenuSubMenuBuilder.get()
				.setCaption("Member")
				.setIcon(VaadinIcons.USERS)
				.setConfig(hybridMenu.getConfig())
				.build(hybridMenu);

		memberList.addLeftMenuButton(LeftMenuButtonBuilder.get()
				.withCaption("Member")
				.withIcon(VaadinIcons.USER)
				.withNavigateTo(MemberPage.class)
				.build());

		memberList.addLeftMenuButton(LeftMenuButtonBuilder.get()
				.withCaption("Group")
				.withIcon(VaadinIcons.USERS)
				.withNavigateTo(GroupPage.class)
				.build());

		MenuSubMenu demoSettings = LeftMenuSubMenuBuilder.get()
				.setCaption("Settings")
				.setIcon(VaadinIcons.COGS)
				.setConfig(hybridMenu.getConfig())
				.build(hybridMenu);

		LeftMenuButtonBuilder.get()
				.withCaption("White Theme")
				.withIcon(VaadinIcons.PALETE)
				.withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteDesign()))
				.build(demoSettings);

		LeftMenuButtonBuilder.get()
				.withCaption("White Color Theme")
				.withIcon(VaadinIcons.PALETE)
				.withClickListener(e -> hybridMenu.switchTheme(DesignItem.getWhiteBlueDesign()))
				.build(demoSettings);

		LeftMenuButtonBuilder.get()
				.withCaption("Dark Theme")
				.withIcon(VaadinIcons.PALETE)
				.withClickListener(e -> hybridMenu.switchTheme(DesignItem.getDarkDesign()))
				.build(demoSettings);

		LeftMenuButtonBuilder.get()
				.withCaption("Toggle MinimalView")
				.withIcon(VaadinIcons.PALETE)
				.withClickListener(e -> hybridMenu.setLeftMenuMinimal(!hybridMenu.isLeftMenuMinimal()))
				.build(demoSettings);
	}

	public HybridMenu getHybridMenu() {
		return hybridMenu;
	}
}

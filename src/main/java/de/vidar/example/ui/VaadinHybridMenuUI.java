package de.vidar.example.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ClientConnector.DetachListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import de.vidar.example.ui.view.ErrorPage;
import de.vidar.example.ui.view.HomePage;
import de.vidar.example.ui.view.SettingsPage;
import kaesdingeling.hybridmenu.HybridMenu;
import kaesdingeling.hybridmenu.builder.HybridMenuBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuButtonBuilder;
import kaesdingeling.hybridmenu.builder.left.LeftMenuSubMenuBuilder;
import kaesdingeling.hybridmenu.builder.top.TopMenuButtonBuilder;
import kaesdingeling.hybridmenu.components.NotificationCenter;
import kaesdingeling.hybridmenu.data.DesignItem;
import kaesdingeling.hybridmenu.data.MenuConfig;
import kaesdingeling.hybridmenu.data.enums.EMenuComponents;
import kaesdingeling.hybridmenu.data.leftmenu.MenuButton;
import kaesdingeling.hybridmenu.data.leftmenu.MenuSubMenu;

@SpringUI
@Theme("mytheme")
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("HybridMenu Spring Boot Test")
@SuppressWarnings("serial")
public class VaadinHybridMenuUI extends UI implements DetachListener {

	private final SpringViewProvider viewProvider;

	private HybridMenu hybridMenu = null;
	private NotificationCenter notificationCenter = null;

	public VaadinHybridMenuUI(SpringViewProvider viewProvider) {
		this.viewProvider = viewProvider;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {

		MenuConfig menuConfig = new MenuConfig();
		menuConfig.setDesignItem(DesignItem.getDarkDesign());

		this.notificationCenter = new NotificationCenter(1);

		this.hybridMenu = HybridMenuBuilder.get()
				.setContent(new VerticalLayout())
				.setMenuComponent(EMenuComponents.LEFT_WITH_TOP)
				.setConfig(menuConfig)
				.withNotificationCenter(notificationCenter)
				.build();

		MenuButton homeButton = LeftMenuButtonBuilder.get()
				.withCaption("Home")
				.withIcon(VaadinIcons.HOME)
				.withNavigateTo(HomePage.class)
				.build();

		hybridMenu.addLeftMenuButton(homeButton);

		MenuSubMenu memberList = LeftMenuSubMenuBuilder.get()
				.setCaption("Member")
				.setIcon(VaadinIcons.USERS)
				.setConfig(hybridMenu.getConfig())
				.build(hybridMenu);

		memberList.addLeftMenuButton(LeftMenuButtonBuilder.get()
				.withCaption("Settings")
				.withIcon(VaadinIcons.COGS)
				.withNavigateTo(SettingsPage.class)
				.build());

		TopMenuButtonBuilder.get()
				.setCaption("Home")
				.setIcon(VaadinIcons.HOME)
				.setAlignment(Alignment.MIDDLE_RIGHT)
				.setNavigateTo(HomePage.class)
				.build(hybridMenu);

		setContent(hybridMenu);

		Navigator navigator = new Navigator(this, hybridMenu.getContent());
		navigator.setErrorView(ErrorPage.class);
		navigator.addProvider(viewProvider);

		UI.getCurrent().getNavigator().addView(HomePage.class.getSimpleName(), HomePage.class);
		UI.getCurrent().getNavigator().addView(SettingsPage.class.getSimpleName(), SettingsPage.class);
	}

	@Override
	public void detach(DetachEvent event) {
		getUI().close();
	}
}
